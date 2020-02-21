package br.com.game.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameStatusDtoTest {

    private static String GAME_NAME = "game_01";
    private static String PLAYER_ISGALAMIDO = "Isgalamido";
    private static String PLAYER_DONO = "Dono";
    private static String PLAYER_MOCINHA = "Mocinha";
    private static int TOTAL_KILLS = 10;

    @Test
    public void testMainMethods(){
        assertThat(GameStatusDto.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
                hasValidBeanEquals(), hasValidBeanToString(), hasValidBeanHashCode()));
    }

    @Test
    public void testAttributeValues(){
        GameStatusDto gameStatusDto = getGameStatusDto();
        assertEquals(GAME_NAME, gameStatusDto.getGameName());
        assertEquals(TOTAL_KILLS, gameStatusDto.getTotalKills());
        assertTrue("Isgalamido é um player?", gameStatusDto.getPlayers().contains(PLAYER_ISGALAMIDO));
        assertTrue("Dono é um player?", gameStatusDto.getPlayers().contains(PLAYER_DONO));
        assertTrue("Mocinha é um player?", gameStatusDto.getPlayers().contains(PLAYER_MOCINHA));
    }

    public static GameStatusDto getGameStatusDto() {
        return GameStatusDto
                .builder()
                .gameName("game_01")
                .players(getPlayers())
                .kills(getKills())
                .totalKills(10)
                .build();
    }

    private static Set<String> getPlayers() {
        Set<String> set = new HashSet<>();
        set.add("Isgalamido");
        set.add("Dono");
        set.add("Mocinha");

        return set;
    }

    private static HashMap<String, Integer> getKills() {
        HashMap<String, Integer> kills = new HashMap<>();
        kills.put("Isgalamido", 2);
        kills.put("Dono", 3);
        kills.put("Mocinha", 5);

        return kills;
    }
}