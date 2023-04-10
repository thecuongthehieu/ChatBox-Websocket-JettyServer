package com.dev;

import io.prometheus.client.exporter.MetricsServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Daemon {
	private static final Logger LOGGER = Logger.getLogger(Daemon.class);
	private static final int SERVER_PORT = 6873;

	public static void main(String[] args) throws Exception {
		Server server = new Server(SERVER_PORT);

		HandlerList handlerList = new HandlerList();

		ContextHandler pingContextHandler = new ContextHandler();
		pingContextHandler.setContextPath("/ping");
		pingContextHandler.setHandler(new PingHandler());
		handlerList.addHandler(pingContextHandler);

		ContextHandler metricsContextHandler = new ContextHandler();
		metricsContextHandler.setContextPath("/metrics");
		metricsContextHandler.setHandler(new MetricsHandler());
		handlerList.addHandler(metricsContextHandler);

		ServletContextHandler sessionServletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		sessionServletContextHandler.setContextPath("/session");
		sessionServletContextHandler.addServlet(new ServletHolder(new ChatWebSocketServlet()), "/chat");
		handlerList.addHandler(sessionServletContextHandler);

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String webappResourceDirPath = classLoader.getResource("webapp").toString();
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(webappResourceDirPath);

		ContextHandler chatboxContextHandler = new ContextHandler();
		chatboxContextHandler.setContextPath("/chatbox");
		chatboxContextHandler.setHandler(resourceHandler);
		handlerList.addHandler(chatboxContextHandler);

		server.setHandler(handlerList);

		server.start();

		LOGGER.trace("Started Server");
		LOGGER.debug("Started Server");
		LOGGER.info("Started Server");
		LOGGER.warn("Started Server");
		LOGGER.fatal("Started Server");

		server.join();
		// server.stop();
	}

	private static class PingHandler extends AbstractHandler {
		@Override
		public void handle(String target, Request req, HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getOutputStream().write("pong".getBytes());
			response.getOutputStream().close();
		}
	}

	private static class MetricsHandler extends AbstractHandler {
		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			MetricsServlet metricsServlet = new MetricsServlet();
			metricsServlet.service(request, response);
			// baseRequest.setHandled(true);
		}
	}
}
