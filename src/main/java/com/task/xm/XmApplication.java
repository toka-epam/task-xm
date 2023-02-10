package com.task.xm;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.task.xm.model.CryptoData;
import com.task.xm.repository.CryptoDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class XmApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CryptoDataRepository cryptoDataRepository){
		return args -> {
			String currentDirectory = Paths.get("")
					.toAbsolutePath() + "/src/main/resources/data/";
			Set<String> fileNames = listFiles(currentDirectory);

			fileNames.forEach(x -> {
				try {
					String absoluteFilePath = currentDirectory + x;
					List<String[]> allLines = readAllLines(absoluteFilePath);
					List<CryptoData> cryptoList = convertToCryptoObject(allLines);
					cryptoDataRepository.saveAll(cryptoList);
				} catch (IOException | CsvException e) {
					throw new RuntimeException(e);
				}
			});
		};
	}
	private List<String[]> readAllLines(String fileName) throws IOException, CsvException {
		try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
			List<String[]> records = reader.readAll();
			records.remove(0);
			return records;
		}
	}
	private List<CryptoData> convertToCryptoObject(List<String[]> records){
		List<CryptoData> cryptoList = new LinkedList<>();
		for(String[] record : records){
			Timestamp timestamp = new Timestamp(Long.parseLong(record[0]));
			LocalDateTime localDateTime = timestamp.toLocalDateTime();

			String cryptoName = record[1];
			BigDecimal price = new BigDecimal(record[2]);

			CryptoData crypto = CryptoData.builder()
					.date(localDateTime)
					.name(cryptoName)
					.price(price).build();
			cryptoList.add(crypto);
		}
		return cryptoList;
	}

	private Set<String> listFiles(String dir) {
		return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
				.filter(file -> !file.isDirectory())
				.map(File::getName)
				.collect(Collectors.toSet());
	}
}
