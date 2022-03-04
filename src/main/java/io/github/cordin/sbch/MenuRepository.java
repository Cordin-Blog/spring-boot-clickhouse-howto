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

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Provides historical data on the menus.
 * 
 * <p>
 * It expects to access a ClickHouse database, with a <em>menu_item_denorm</em> table having the
 * schema defined in the <em>schema-clickhouse-denormalize.sql</em> file, and having data imported
 * from the <a href="http://menus.nypl.org/data">'What's on the Menu?' dataset</a>.
 * </p>
 * @author Cèsar Ordiñana
 */
@Component
public class MenuRepository {

    private static final String AVERAGED_DISH_PRICE_QUERY = """
            SELECT
                round(toUInt32OrZero(extract(menu_date, '^\\d{4}')), -1) AS decade,
                count() AS dishes,
                round(avg(price), 2) as avgPrice
            FROM menu_item_denorm
            WHERE (menu_currency = 'Dollars') AND (decade > 0) AND (decade < 2022)
            GROUP BY decade
            ORDER BY decade ASC;
              """;

    private final JdbcTemplate template;

    /**
     * Creates a new instance.
     * @param template to use to perform JDBC queries to the ClickHouse database.
     */
    MenuRepository(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Provides the Averaged historical prices of dishes by decade.
     * 
     * @see https://clickhouse.com/docs/en/getting-started/example-datasets/menus/#query-averaged-historical-prices
     * @return the average dish price by decade
     */
    public List<Map<String, Object>> averagedDishPrice() {
        return template.queryForList(AVERAGED_DISH_PRICE_QUERY);
    }
}
