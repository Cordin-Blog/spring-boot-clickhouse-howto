/*
 *  Copyright 2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.cordin.sbch;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides a JSON web API to get data about menu historical data.
 * @author Cèsar Ordiñana
 */
@RestController
class MenuController {

    private final MenuRepository menuRepository;

    /**
     * Creates a new instance.
     * @param menuRepository to get the menu data from
     */
    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Provides the Averaged historical prices of dishes by decade.
     * @return the average dish price by decade as JSON
     */
    @GetMapping("/avgdishprice")
    public ResponseEntity<List<Map<String, Object>>> averagedDishPrice() {
        return ResponseEntity.ok(menuRepository.averagedDishPrice());
    }
}
