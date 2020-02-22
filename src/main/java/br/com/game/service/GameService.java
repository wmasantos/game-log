package br.com.game.service;

import br.com.game.dto.GameStatusDto;
import br.com.game.service.impl.GameServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class GameService implements GameServiceImpl {
    public static final String FILE_NAME = "game.log";
    private static final String WORLD = "<world>";

    private Matcher mClient, mInitGame, mEndGame, mKill;

    @Override
    public File loadData(MultipartFile multipartFile) throws IOException {

        File file = new File(FILE_NAME);

        if(file.exists()) {
            boolean deleteResult = file.delete();
            if (!deleteResult)
                throw new IOException("Arquivo anteriormente enviado não pode ser deletado");
        }

        boolean createResult = file.createNewFile();

        if (!createResult)
            throw new IOException("Um novo arquivo não pôde ser criado");

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }

    @Override
    public List<GameStatusDto> gameBuild(final String fileName) throws IOException {
        final File file = new File(fileName);

        final List<GameStatusDto> gameStatusDtoList = new ArrayList<>();
        GameStatusDto gameStatusDto = null;

        BufferedReader br = new BufferedReader(new FileReader(file));

        initMathers();

        String line;
        int totalGames = 0;
        boolean initGame = false;
        int totalKills = 0;

        while((line = br.readLine()) != null){
            mInitGame.reset(line);
            mEndGame.reset(line);
            mClient.reset(line);
            mKill.reset(line);

            if(mInitGame.find()){
                totalGames = getTotalGames(gameStatusDtoList, gameStatusDto, totalGames, initGame, totalKills);

                initGame = true;
                totalKills = 0;

                gameStatusDto = new GameStatusDto();
                gameStatusDto.setPlayers(new HashSet<>());
                gameStatusDto.setKills(new HashMap<>());
            }
            else if(mEndGame.find()){
                totalGames = getTotalGames(gameStatusDtoList, gameStatusDto, totalGames, initGame, totalKills);

                initGame = false;
            }
            else if(mClient.find()){
                gameStatusDto.getPlayers().add(getplayer(line));
                gameStatusDto.getKills().putIfAbsent(getplayer(line), 0);
            }
            else if(mKill.find()){
                totalKills++;
                if(line.contains(GameService.WORLD)) {
                    String name = getplayerKilled(line);
                    int kill = gameStatusDto.getKills().get(name);
                    gameStatusDto.getKills().put(name, kill-1);
                }
                else {
                    String killer = getplayerKiller(line);
                    int kill = gameStatusDto.getKills().get(killer);
                    gameStatusDto.getKills().put(killer, kill+1);
                }
            }
        }

        br.close();
        return gameStatusDtoList;
    }

    @Override
    public GameStatusDto getGameById(final Integer gameId) throws IOException{
        List<GameStatusDto> gameStatusDtoList = gameBuild(GameService.FILE_NAME).stream()
                .filter(
                        gameStatus -> gameStatus.getGameName().equals("game_" + gameId))
                .collect(Collectors.toList());

        return gameStatusDtoList.isEmpty() ? null : gameStatusDtoList.get(0);
    }

    @Override
    public HashMap<String, GameStatusDto> getWithHash(String file) throws IOException{
        List<GameStatusDto> gameStatusEntities = gameBuild(file);

        HashMap<String, GameStatusDto> res = new HashMap<>();

        int games = 0;

        for(GameStatusDto game: gameStatusEntities){
            games++;
            res.put("game_" + games, game);
        }

        return res;
    }

    private void initMathers(){
        Pattern pClient = Pattern.compile("\\w*(ClientUserinfoChanged)\\w*");
        mClient = pClient.matcher("");

        Pattern pInitGame = Pattern.compile("\\w*(InitGame)\\w*");
        mInitGame = pInitGame.matcher("");

        Pattern pEndGame = Pattern.compile("\\w*(ShutdownGame)\\w*");
        mEndGame = pEndGame.matcher("");

        Pattern pKill = Pattern.compile("\\w*(Kill)\\w*");
        mKill = pKill.matcher("");
    }

    private int getTotalGames(List<GameStatusDto> gameStatusDtoList, GameStatusDto gameStatusDto, int totalGames,
                              boolean initGame, int totalKills) {
        if(initGame){
            totalGames++;
            gameStatusDto.setGameName("game_" + totalGames);
            gameStatusDtoList.add(gameStatusDto);
            gameStatusDto.setTotalKills(totalKills);
        }
        return totalGames;
    }

    private String getplayer(String line){
        int index1 = line.indexOf("n\\");
        int index2 = line.indexOf("\\t");

        return line.substring(index1+2, index2);
    }

    private String getplayerKilled(String line){
        int index1 = line.indexOf("killed ");
        int index2 = line.indexOf(" by");

        return line.substring(index1+7, index2);
    }

    private String getplayerKiller(String line){
        int index1 = line.lastIndexOf(": ");
        int index2 = line.indexOf(" killed");

        return line.substring(index1+2, index2);
    }
}
