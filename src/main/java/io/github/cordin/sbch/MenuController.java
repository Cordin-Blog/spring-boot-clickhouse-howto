/**
 * 
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
	public ResponseEntity<List<Map<String,Object>>> averagedDishPrice() {
		return ResponseEntity.ok(menuRepository.averagedDishPrice());
	}
}
