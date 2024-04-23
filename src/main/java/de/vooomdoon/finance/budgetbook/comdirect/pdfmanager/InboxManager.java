package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * DOCME add JavaDoc for method parseLocalDate
	 * 
	 * @param name
	 * @return
	 * @since 0.1.0
	 */
	private static LocalDate parseLocalDate(String name) {
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
	 * @since 0.1.0
	 */
	public void run(Path inboxDirectory) {
		logger.debug("run " + inboxDirectory);

		for (File file : inboxDirectory.toFile().listFiles()) {
			processFile(file);
		}
	}

	/**
	 * DOCME add JavaDoc for method processFile
	 * 
	 * @param inputFile
	 * @since 0.1.0
	 */
	private void processFile(File inputFile) {
		logger.debug("processFile " + inputFile);

		StringBuilder sb = new StringBuilder();
		sb.append("Finanzreport_");
		sb.append(parseLocalDate(inputFile.getName()).format(OUTPUT_DATE_TIME_FORMATTER));
		sb.append(".pdf");

		File outputFile = new File(inputFile.getParentFile(), sb.toString());
		logger.debug("outputFile: " + outputFile);

		boolean success = inputFile.renameTo(outputFile);
		logger.debug("success: " + success);
	}
}
