package de.najidev.mensaupb;

import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

import de.najidev.mensaupb.entity.MenuRepository;

import roboguice.application.RoboApplication;

public class Application extends RoboApplication
{
	@Override
	protected void addApplicationModules(List<Module> modules)
	{
		modules.add(new AbstractModule()
		{	
			@Override
			protected void configure()
			{
				requestStaticInjection(MenuRepository.class);
				requestInjection(MenuRepository.class);
				requestInjection(ApplicationContext.class);
			}
		});
	}
}