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

import com.digid.eddokenaCM.FTP.io.SocketInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RCommandClient extends RExecClient
{
    /***
     * The default rshell port.  Set to 514 in BSD Unix.
     ***/
    public static final int DEFAULT_PORT = 514;

    /***
     * The smallest port number an rcmd client may use.  By BSD convention
     * this number is 512.
     ***/
    public static final int MIN_CLIENT_PORT = 512;

    /***
     * The largest port number an rcmd client may use.  By BSD convention
     * this number is 1023.
     ***/
    public static final int MAX_CLIENT_PORT = 1023;

    // Overrides method in RExecClient in order to implement proper
    // port number limitations.
    @Override
    InputStream _createErrorStream() throws IOException
    {
        int localPort;
        ServerSocket server;
        Socket socket;

        localPort = MAX_CLIENT_PORT;
        server = null; // Keep compiler from barfing

        for (localPort = MAX_CLIENT_PORT; localPort >= MIN_CLIENT_PORT; --localPort)
        {
            try
            {
                server = _serverSocketFactory_.createServerSocket(localPort, 1,
                         getLocalAddress());
                break; // got a socket
            }
            catch (SocketException e)
            {
                continue;
            }
        }

        if (server == null) {
            throw new BindException("All ports in use.");
        }

        _output_.write(Integer.toString(server.getLocalPort()).getBytes());
        _output_.write('\0');
        _output_.flush();

        socket = server.accept();
        server.close();

        if (isRemoteVerificationEnabled() && !verifyRemote(socket))
        {
            socket.close();
            throw new IOException(
                "Security violation: unexpected connection attempt by " +
                socket.getInetAddress().getHostAddress());
        }

        return (new SocketInputStream(socket, socket.getInputStream()));
    }

    /***
     * The default RCommandClient constructor.  Initializes the
     * default port to <code> DEFAULT_PORT </code>.
     ***/
    public RCommandClient()
    {
        setDefaultPort(DEFAULT_PORT);
    }

    public void connect(InetAddress host, int port, InetAddress localAddr)
    throws SocketException, BindException, IOException
    {
        int localPort;

        localPort = MAX_CLIENT_PORT;

        for (localPort = MAX_CLIENT_PORT; localPort >= MIN_CLIENT_PORT; --localPort)
        {
            try
            {
                _socket_ =
                    _socketFactory_.createSocket(host, port, localAddr, localPort);
            }
            catch (BindException be) {
                continue;
            }
            catch (SocketException e)
            {
                continue;
            }
            break;
        }

        if (localPort < MIN_CLIENT_PORT)
            throw new BindException("All ports in use or insufficient permssion.");

        _connectAction_();
    }


    @Override
    public void connect(InetAddress host, int port)
    throws SocketException, IOException
    {
        connect(host, port, InetAddress.getLocalHost());
    }

    @Override
    public void connect(String hostname, int port)
    throws SocketException, IOException, UnknownHostException
    {
        connect(InetAddress.getByName(hostname), port, InetAddress.getLocalHost());
    }

    public void connect(String hostname, int port, InetAddress localAddr)
    throws SocketException, IOException
    {
        connect(InetAddress.getByName(hostname), port, localAddr);
    }

    @Override
    public void connect(InetAddress host, int port,
                        InetAddress localAddr, int localPort)
    throws SocketException, IOException, IllegalArgumentException
    {
        if (localPort < MIN_CLIENT_PORT || localPort > MAX_CLIENT_PORT)
            throw new IllegalArgumentException("Invalid port number " + localPort);
        super.connect(host, port, localAddr, localPort);
    }

    @Override
    public void connect(String hostname, int port,
                        InetAddress localAddr, int localPort)
    throws SocketException, IOException, IllegalArgumentException, UnknownHostException
    {
        if (localPort < MIN_CLIENT_PORT || localPort > MAX_CLIENT_PORT)
            throw new IllegalArgumentException("Invalid port number " + localPort);
        super.connect(hostname, port, localAddr, localPort);
    }

    public void rcommand(String localUsername, String remoteUsername,
                         String command, boolean separateErrorStream)
    throws IOException
    {
        rexec(localUsername, remoteUsername, command, separateErrorStream);
    }


    /***
     * Same as
     * <code> rcommand(localUsername, remoteUsername, command, false); </code>
     ***/
    public void rcommand(String localUsername, String remoteUsername,
                         String command)
    throws IOException
    {
        rcommand(localUsername, remoteUsername, command, false);
    }

}

