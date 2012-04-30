package de.najidev.mensaupb.helper;

import de.najidev.mensaupb.entity.MenuRepository;

public class ServiceContainer
{	
	protected static ServiceContainer instance = null;

	protected boolean initialized = false;
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

		// prepare context
		this.context = new Context();

		// prepare menu repository
		this.menuRepository = new MenuRepository(
				this.context,
				new DatabaseHelper(applicationContext)
				);

		this.initialized = true;

		return this;
	}

	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public MenuRepository getMenuRepository()
	{
		return menuRepository;
	}

	public void setMenuRepository(MenuRepository menuRepository)
	{
		this.menuRepository = menuRepository;
	}

	public boolean isInitialized()
	{
		return this.initialized;
	}
}