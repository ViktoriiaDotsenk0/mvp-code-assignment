package com.skai.mvpassignment;

import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class MvpCodeAssignmentApplication {
    @Value("${tournament.directory}")
    private String gameDirPath;
    private final TournamentService tournamentService;

    public static void main(String[] args) {
        SpringApplication.run(MvpCodeAssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            try{
                File gameDir = new File(gameDirPath);
                List<PlayerData> tournamentParticipants = tournamentService.getTournamentPlayers(gameDir);
                System.out.println("Tournament participants: ");
                for (PlayerData participant : tournamentParticipants) {
                    System.out.println(participant);
                }
                System.out.println("--------------------------------");
                System.out.println("Tournament MVP:");
                for (PlayerData playerData : tournamentService.getMVP(tournamentParticipants)) {
                    System.out.println(playerData);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }
        };
    }
}
