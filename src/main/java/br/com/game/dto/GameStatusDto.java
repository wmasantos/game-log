package br.com.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameStatusDto implements Serializable {
    private String gameName;
    private int totalKills;
    private Set<String> players;
    private HashMap<String, Integer> kills;
}
