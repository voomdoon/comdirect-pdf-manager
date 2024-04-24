package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import de.voomdoon.logging.LogManager;
import de.voomdoon.logging.Logger;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class InboxManager {

	/**
	 * @since 0.1.0
	 */
	private static final DateTimeFormatter INPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	/**
	 * @since 0.1.0
	 */
	private static final String NAME_PREFIX = "Finanzreport";

	/**
	 * @since 0.1.0
	 */
	private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * DOCME add JavaDoc for method parseLocalDate
	 * 
	 * @param name
	 * @return
	 * @since 0.1.0
	 */
	private static LocalDate parseInputDate(String name) {
		int start = name.indexOf("per_") + 4;
		int end = start + 10;

		return LocalDate.parse(name.substring(start, end), INPUT_DATE_TIME_FORMATTER);
	}

	/**
	 * @since 0.1.0
	 */
	private final Logger logger = LogManager.getLogger(getClass());

	/**
	 * DOCME add JavaDoc for method run
	 * 
	 * @param inboxDirectory
	 * @throws IOException
	 * @since 0.1.0
	 */
	public void run(Path inboxDirectory) throws IOException {
		logger.debug("run " + inboxDirectory);

		ensureFileNames(inboxDirectory);

		groupFiles(inboxDirectory);
	}

	/**
	 * DOCME add JavaDoc for method processFile
	 * 
	 * @param inputFile
	 * @since 0.1.0
	 */
	private void ensureFileName(File inputFile) {
		logger.debug("ensureFileName " + inputFile);

		if (!inputFile.getName().contains("per")) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(NAME_PREFIX).append("_");
		sb.append(parseInputDate(inputFile.getName()).format(OUTPUT_DATE_TIME_FORMATTER));
		sb.append(".pdf");

		File outputFile = new File(inputFile.getParentFile(), sb.toString());
		logger.debug("outputFile: " + outputFile);

		boolean success = inputFile.renameTo(outputFile);
		logger.debug("success: " + success);
	}

	/**
	 * @param inboxDirectory
	 * @since 0.1.0
	 */
	private void ensureFileNames(Path inboxDirectory) {
		for (File file : inboxDirectory.toFile().listFiles()) {
			ensureFileName(file);
		}
	}

	/**
	 * DOCME add JavaDoc for method getOutputFileName
	 * 
	 * @param file
	 * @param inboxDirectory
	 * @return
	 * @since 0.1.0
	 */
	private String getOutputFileName(File file, Path inboxDirectory) {
		int start = file.getName().indexOf("_") + 1;
		int end = file.getName().indexOf(".pdf");
		LocalDate date = parseOutputDate(file.getName().substring(start, end));
		logger.debug("date: " + date);

		StringBuilder sb = new StringBuilder();
		sb.append(inboxDirectory);
		sb.append("/");
		sb.append(date.getYear());
		sb.append("/");
		sb.append(file.getName());

		return sb.toString();
	}

	/**
	 * DOCME add JavaDoc for method groupFile
	 * 
	 * @param file
	 * @param inboxDirectory
	 * @throws IOException
	 * @since 0.1.0
	 */
	private void groupFile(File file, Path inboxDirectory) throws IOException {
		logger.debug("groupFile " + file);
		String outputFileName = getOutputFileName(file, inboxDirectory);
		logger.debug("outputFileName: " + outputFileName);

		File outputFile = new File(outputFileName);
		outputFile.getParentFile().mkdirs();

		Files.move(file.getAbsoluteFile().toPath(), outputFile.getAbsoluteFile().toPath());
	}

	/**
	 * DOCME add JavaDoc for method groupFiles
	 * 
	 * @param inboxDirectory
	 * @throws IOException
	 * @since 0.1.0
	 */
	private void groupFiles(Path inboxDirectory) throws IOException {
		List<File> files = Files.walk(inboxDirectory).map(Path::toFile).filter(File::isFile)
				.filter(file -> file.getName().startsWith(NAME_PREFIX)).toList();

		for (File file : files) {
			groupFile(file, inboxDirectory);
		}
	}

	/**
	 * DOCME add JavaDoc for method parseOutputDate
	 * 
	 * @param string
	 * @return
	 * @since 0.1.0
	 */
	private LocalDate parseOutputDate(String string) {
		return LocalDate.parse(string, OUTPUT_DATE_TIME_FORMATTER);
	}
}
