// Lab 3
package com.tus.guitarinventory.service;

import com.tus.guitarinventory.dto.InventoryDto;

public interface IInventoryService {

    /**
     * @param inventoryDto - InventoryDto Object
     */
    void createGuitar(InventoryDto inventoryDto);

    /**
     * @param serialNumber - Input Serial Number
     * @return Guitar Details based on a given serialNumber
     */
    InventoryDto fetchGuitar(String serialNumber);

    /**
     * @param inventoryDto - InventoryDto Object
     * @return boolean indicating if update of guitar details is successful or not
     */
    boolean updateGuitar(InventoryDto inventoryDto);

    /**
     * @param serialNumber - Input Serial Number
     * @return boolean indicating if delete of guitar details is successful or not
     */
    boolean deleteGuitar(String serialNumber);
}