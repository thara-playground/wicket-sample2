/*
* Copyright 2011 Tomochika Hara.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.tomochika1985.addressbook.domain.inmemory;

import java.util.List;

import org.tomochika1985.addressbook.domain.AddressEntry;
import org.tomochika1985.addressbook.domain.ContactEntry;
import org.tomochika1985.addressbook.domain.ContactRepository;

/**
 * @author t_hara
 *
 */
public class ContactInmemoryRepository implements ContactRepository {

	@Override
	public List<ContactEntry> findAllContacts() {
		return null;
	}

	@Override
	public List<AddressEntry> findAllAddressesForContact(String contactId) {
		return null;
	}

	@Override
	public List<AddressEntry> findAllAddressesInCityForContact(String name,
			String city) {
		return null;
	}

	@Override
	public ContactEntry loadContactDetails(String contactId) {
		return null;
	}
}
