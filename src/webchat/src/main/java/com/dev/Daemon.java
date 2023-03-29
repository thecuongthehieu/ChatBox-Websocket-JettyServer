package com.dev;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Daemon {
	public static void main(String[] args) throws Exception {
		Server server = new Server(9999);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(new ChatWebSocketServlet()), "/chat");

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase("src/main/resources");

		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(new Handler[] {resourceHandler, context});
		server.setHandler(handlerList);

		server.start();
		System.out.println("Started servlet");
		server.join();
	}
}
