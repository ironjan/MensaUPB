package de.najidev.mensaupb.helper;

import de.najidev.mensaupb.entity.MenuRepository;

public class ServiceContainer
{	
	protected static ServiceContainer instance = null;

	protected boolean initialized = false;
	protected Configuration configuration;
	protected Context context;
	protected MenuRepository menuRepository;

	private ServiceContainer() { }

	public static ServiceContainer getInstance()
	{
		if (null == ServiceContainer.instance)
			ServiceContainer.instance = new ServiceContainer();

		return ServiceContainer.instance;
	}

	public ServiceContainer initialize(android.content.Context applicationContext) throws Exception
	{
		if (this.initialized)
			throw new Exception("Service container can only once be initialized");
		
		// prepare database
		DatabaseHelper databaseHelper = new DatabaseHelper(applicationContext);
		
		// prepare configuration
		configuration = new Configuration(databaseHelper);

		// prepare context
		this.context = new Context(configuration);

		// prepare menu repository
		this.menuRepository = new MenuRepository(this.context, databaseHelper);

		this.initialized = true;

		return this;
	}

	public Context getContext()
	{
		return context;
	}

	public MenuRepository getMenuRepository()
	{
		return menuRepository;
	}

	public Configuration getConfiguration()
	{
		return configuration;
	}
	
	public boolean isInitialized()
	{
		return this.initialized;
	}
}