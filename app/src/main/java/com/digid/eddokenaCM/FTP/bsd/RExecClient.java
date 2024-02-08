/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digid.eddokenaCM.FTP.bsd;

import com.digid.eddokenaCM.FTP.SocketClient;
import com.digid.eddokenaCM.FTP.io.SocketInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class RExecClient extends SocketClient
{
    /***
     * The default rexec port.  Set to 512 in BSD Unix.
     ***/
    public static final int DEFAULT_PORT = 512;

    private boolean __remoteVerificationEnabled;

    /***
     * If a separate error stream is requested, <code>_errorStream_</code>
     * will point to an InputStream from which the standard error of the
     * remote process can be read (after a call to rexec()).  Otherwise,
     * <code> _errorStream_ </code> will be null.
     ***/
    protected InputStream _errorStream_;

    // This can be overridden in local package to implement port range
    // limitations of rcmd and rlogin
    InputStream _createErrorStream() throws IOException
    {
        ServerSocket server;
        Socket socket;

        server = _serverSocketFactory_.createServerSocket(0, 1, getLocalAddress());

        _output_.write(Integer.toString(server.getLocalPort()).getBytes());
        _output_.write('\0');
        _output_.flush();

        socket = server.accept();
        server.close();

        if (__remoteVerificationEnabled && !verifyRemote(socket))
        {
            socket.close();
            throw new IOException(
                "Security violation: unexpected connection attempt by " +
                socket.getInetAddress().getHostAddress());
        }

        return (new SocketInputStream(socket, socket.getInputStream()));
    }


    /***
     * The default RExecClient constructor.  Initializes the
     * default port to <code> DEFAULT_PORT </code>.
     ***/
    public RExecClient()
    {
        _errorStream_ = null;
        setDefaultPort(DEFAULT_PORT);
    }


    /***
     * Returns the InputStream from which the standard outputof the remote
     * process can be read.  The input stream will only be set after a
     * successful rexec() invocation.
     * <p>
     * @return The InputStream from which the standard output of the remote
     * process can be read.
     ***/
    public InputStream getInputStream()
    {
        return _input_;
    }


    /***
     * Returns the OutputStream through which the standard input of the remote
     * process can be written.  The output stream will only be set after a
     * successful rexec() invocation.
     * <p>
     * @return The OutputStream through which the standard input of the remote
     * process can be written.
     ***/
    public OutputStream getOutputStream()
    {
        return _output_;
    }


    /***
     * Returns the InputStream from which the standard error of the remote
     * process can be read if a separate error stream is requested from
     * the server.  Otherwise, null will be returned.  The error stream
     * will only be set after a successful rexec() invocation.
     * <p>
     * @return The InputStream from which the standard error of the remote
     * process can be read if a separate error stream is requested from
     * the server.  Otherwise, null will be returned.
     ***/
    public InputStream getErrorStream()
    {
        return _errorStream_;
    }


    /***
     * Remotely executes a command through the rexecd daemon on the server
     * to which the RExecClient is connected.  After calling this method,
     * you may interact with the remote process through its standard input,
     * output, and error streams.  You will typically be able to detect
     * the termination of the remote process after reaching end of file
     * on its standard output (accessible through
     * {@link #getInputStream  getInputStream() }.    Disconnecting
     * from the server or closing the process streams before reaching
     * end of file will not necessarily terminate the remote process.
     * <p>
     * If a separate error stream is requested, the remote server will
     * connect to a local socket opened by RExecClient, providing an
     * independent stream through which standard error will be transmitted.
     * RExecClient will do a simple security check when it accepts a
     * connection for this error stream.  If the connection does not originate
     * from the remote server, an IOException will be thrown.  This serves as
     * a simple protection against possible hijacking of the error stream by
     * an attacker monitoring the rexec() negotiation.  You may disable this
     * behavior with {@link #setRemoteVerificationEnabled setRemoteVerificationEnabled()}
     * .
     * <p>
     * @param username  The account name on the server through which to execute
     *                  the command.
     * @param password  The plain text password of the user account.
     * @param command   The command, including any arguments, to execute.
     * @param separateErrorStream True if you would like the standard error
     *        to be transmitted through a different stream than standard output.
     *        False if not.
     * @exception IOException If the rexec() attempt fails.  The exception
     *            will contain a message indicating the nature of the failure.
     ***/
    public void rexec(String username, String password,
                      String command, boolean separateErrorStream)
    throws IOException
    {
        int ch;

        if (separateErrorStream)
        {
            _errorStream_ = _createErrorStream();
        }
        else
        {
            _output_.write('\0');
        }

        _output_.write(username.getBytes());
        _output_.write('\0');
        _output_.write(password.getBytes());
        _output_.write('\0');
        _output_.write(command.getBytes());
        _output_.write('\0');
        _output_.flush();

        ch = _input_.read();
        if (ch > 0)
        {
            StringBuilder buffer = new StringBuilder();

            while ((ch = _input_.read()) != -1 && ch != '\n')
                buffer.append((char)ch);

            throw new IOException(buffer.toString());
        }
        else if (ch < 0)
        {
            throw new IOException("Server closed connection.");
        }
    }


    /***
     * Same as <code> rexec(username, password, command, false); </code>
     ***/
    public void rexec(String username, String password,
                      String command)
    throws IOException
    {
        rexec(username, password, command, false);
    }

    /***
     * Disconnects from the server, closing all associated open sockets and
     * streams.
     * <p>
     * @exception IOException If there an error occurs while disconnecting.
     ***/
    @Override
    public void disconnect() throws IOException
    {
        if (_errorStream_ != null)
            _errorStream_.close();
        _errorStream_ = null;
        super.disconnect();
    }


    /***
     * Enable or disable verification that the remote host connecting to
     * create a separate error stream is the same as the host to which
     * the standard out stream is connected.  The default is for verification
     * to be enabled.  You may set this value at any time, whether the
     * client is currently connected or not.
     * <p>
     * @param enable True to enable verification, false to disable verification.
     ***/
    public final void setRemoteVerificationEnabled(boolean enable)
    {
        __remoteVerificationEnabled = enable;
    }

    /***
     * Return whether or not verification of the remote host providing a
     * separate error stream is enabled.  The default behavior is for
     * verification to be enabled.
     * <p>
     * @return True if verification is enabled, false if not.
     ***/
    public final boolean isRemoteVerificationEnabled()
    {
        return __remoteVerificationEnabled;
    }

}

