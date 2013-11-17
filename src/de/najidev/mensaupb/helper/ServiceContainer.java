package de.najidev.mensaupb.helper;

import org.slf4j.*;

import de.najidev.mensaupb.entity.*;

public class ServiceContainer {

	protected static ServiceContainer instance = null;

	protected boolean initialized = false;
	protected Configuration configuration;
	protected Context context;
	protected MenuRepository menuRepository;

	private static final String TAG = ServiceContainer.class.getName();
	private static final Logger LOGGER = LoggerFactory.getLogger(TAG);

	private ServiceContainer() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Created new {}", TAG);
		}
	}

	public static ServiceContainer getInstance() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getInstance()");
		}
		if (null == ServiceContainer.instance) {
			ServiceContainer.instance = new ServiceContainer();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getInstance() -> {}", instance);
		}
		return ServiceContainer.instance;
	}

	public ServiceContainer initialize(
			final android.content.Context applicationContext) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("initialize({})", applicationContext);
		}

		if (initialized) {
			LOGGER.warn("Service contrainer was already initialized.");
			throw new Exception(
					"Service container can only once be initialized");
		}

		// prepare database
		final DatabaseHelper databaseHelper = new DatabaseHelper(
				applicationContext);

		// prepare configuration
		configuration = new Configuration(databaseHelper);

		// prepare context
		context = new Context(configuration);

		// prepare menu repository
		menuRepository = new MenuRepository(context, databaseHelper);

		initialized = true;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("initialize({}) -> {}", applicationContext, this);
		}
		return this;
	}

	public Context getContext() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getContext() -> {}", context);
		}
		return context;
	}

	public MenuRepository getMenuRepository() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getMenuRepository() -> {}", menuRepository);
		}
		return menuRepository;
	}

	public Configuration getConfiguration() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getConfiguration() -> {}", configuration);
		}
		return configuration;
	}

	public boolean isInitialized() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("isInitialized() -> {}", initialized);
		}
		return initialized;
	}

	@Override
	public String toString() {
		return "ServiceContainer [initialized=" + initialized
				+ ", configuration=" + configuration + ", context=" + context
				+ ", menuRepository=" + menuRepository + "]";
	}

}