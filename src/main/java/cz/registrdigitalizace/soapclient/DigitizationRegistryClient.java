/*
 * Copyright (C) 2012 Jan Pokorsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.registrdigitalizace.soapclient;

import cz.registrdigitalizace.soapservices.DigitizationRegistry;
import cz.registrdigitalizace.soapservices.DigitizationRegistryService;
import java.util.Map;
import javax.xml.ws.BindingProvider;

/**
 * Client entry point.
 *
 * @author Jan Pokorsky
 */
public final class DigitizationRegistryClient {

    private final DigitizationRegistry registery;

    public DigitizationRegistryClient(String user, String passwd) {
        this(null, user, passwd);
    }

    public DigitizationRegistryClient(String url, String user, String passwd) {
        DigitizationRegistryService service = new DigitizationRegistryService();
        registery = service.getDigitizationRegistryPort();
        Map<String, Object> request = ((BindingProvider) registery).getRequestContext();
        if (url != null) {
            request.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        }
        request.put(BindingProvider.USERNAME_PROPERTY, user);
        request.put(BindingProvider.PASSWORD_PROPERTY, passwd);
    }

    public DigitizationRegistry getRegistery() {
        return registery;
    }

}
