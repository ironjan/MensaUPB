package de.najidev.mensaupb.entity;

import org.slf4j.*;

import java.util.*;

public class Menu {

	private static final String TAG = Menu.class.getSimpleName();
	private Date date;
	private String title;
	private String name;
	private String type;
	private String sides;
	private String location;
	
	private static Logger LOGGER = LoggerFactory.getLogger(TAG);

	
	
	public Menu() {
		super();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Created new {}",TAG);
		}
	}

	public Date getDate() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getDate() -> {}", date);
		}
		return date;
	}

	public void setDate(final Date date) {
		Object[] params = { date };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setDate({})", params);
		}
		this.date = date;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setDate({}) -> {}", params, "VOID");
		}
	}

	public String getTitle() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTitle() -> {}", title);
		}
		return title;
	}

	public void setTitle(final String title) {
		Object[] params = { title };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setTitle({})", params);
		}
		this.title = title;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setTitle({}) -> {}", params, "VOID");
		}
	}

	public String getName() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getName() -> {}", name);
		}
		return name;
	}

	public void setName(final String name) {
		Object[] params = { name };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setName({})", params);
		}
		this.name = name;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setName({}) -> {}", params, "VOID");
		}
	}

	public String getType() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getType() -> {}", type);
		}
		return type;
	}

	public void setType(final String type) {
		Object[] params = { type };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setType({})", params);
		}
		this.type = type;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setType({}) -> {}", params, "VOID");
		}
	}

	public String getSides() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getSides() -> {}", sides);
		}
		return sides;
	}

	public void addSide(final String side) {
		Object[] params = { side };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addSide({})", params);
		}
		if (null == sides) {
			sides = side;
		}
		else {
			sides += ", " + side;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addSide({}) -> {}", params, "VOID");
		}
	}

	public String getLocation() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getLocation() -> {}", location);
		}
		return location;
	}

	public void setLocation(final String location) {
		Object[] params = { location };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setLocation({})", params);
		}
		this.location = location;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setLocation({}) -> {}", params, "VOID");
		}
	}

	public boolean isTagesTipp() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("isTagesTipp()");
		}

		final boolean result;
		if (null != title && title.equals("Tages - Tipp")) {
			result = true;
		}
		else {
			result = false;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("isTagesTipp() -> {}", result);
		}
		return result;
	}

	@Override
	public String toString() {
		return "MenuContent [date=" + date + ", title=" + title + ", name=" + name
				+ ", type=" + type + ", sides=" + sides + ", location="
				+ location + "]";
	}
}