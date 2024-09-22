package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.model.ItemDM;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for generating default images for lists / items.
 */
@Service
public class ImageGeneratorService {

    /**
     * Generates and sets the default item image. Only meant to be used if an image URL has
     * not already been provided.
     * @param item the item to generate the image for
     */
    public void generateDefaultItemImage(final ItemDM item) {
        // TODO: implement me
    }
}
