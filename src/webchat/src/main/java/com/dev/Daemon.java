package com.dev;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.apache.log4j.Logger;

public class Daemon {
	private static final Logger LOGGER = Logger.getLogger(Daemon.class);
	private static final int SERVER_PORT = 6873;

	public static void main(String[] args) throws Exception {
		Server server = new Server(SERVER_PORT);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(new ChatWebSocketServlet()), "/chat");

		Class cl = Class.forName("com.dev.Daemon");
		String indexFilePath = cl.getResource("/index.html").getPath();

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(indexFilePath);

		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(new Handler[] {resourceHandler, context});
		server.setHandler(handlerList);

		server.start();

		LOGGER.trace("Started Server");
		LOGGER.debug("Started Server");
		LOGGER.info("Started Server");
		LOGGER.warn("Started Server");
		LOGGER.fatal("Started Server");

		server.join();
	}
}
