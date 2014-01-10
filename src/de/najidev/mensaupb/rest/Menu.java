package de.najidev.mensaupb.rest;

import java.util.*;

/**
 * Created by ljan on 10.01.14.
 */
public interface Menu {
    Date getDate();

    String getDescription();

    String getName();

    String getType();

    String getPrice();

    String getCounter();

    String[] getSide_dishes();
}
