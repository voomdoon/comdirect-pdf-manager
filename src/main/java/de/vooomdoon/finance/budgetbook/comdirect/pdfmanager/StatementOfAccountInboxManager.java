package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.voomdoon.logging.LogManager;
import de.voomdoon.logging.Logger;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class StatementOfAccountInboxManager {

	/**
	 * @since 0.1.0
	 */
	private static final Pattern FINANZREPORT_INPUT_PATTERN = Pattern
			.compile("Finanzreport_?Nr\\._?\\d{1,3}_?(per|vom)_?(?<date>\\d{2}\\.\\d{2}\\.\\d{4})_?\\w{6}\\.pdf");

	/**
	 * @since 0.1.0
	 */
	private static final Pattern FINANZREPORT_OUTPUT_PATTERN = Pattern
			.compile("Finanzreport_(?<date>\\d{4}-\\d{2}-\\d{2}).pdf");

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
		return LocalDate.parse(name, INPUT_DATE_TIME_FORMATTER);
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

		ensureFileNames(inboxDirectory.toFile());

		groupFiles(inboxDirectory);
	}

	/**
	 * DOCME add JavaDoc for method processFile
	 * 
	 * @param inputFile
	 * @since 0.1.0
	 */
	private void ensureFileName(File inputFile) {
		if (FINANZREPORT_INPUT_PATTERN.matcher(inputFile.getName()).matches()
				|| FINANZREPORT_OUTPUT_PATTERN.matcher(inputFile.getName()).matches()) {
			ensureFinanzreportFileName(inputFile);
		} else {
			logger.trace("ensureFileName: ignore " + inputFile);
		}
	}

	/**
	 * @param directory
	 * @since 0.1.0
	 */
	private void ensureFileNames(File directory) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				ensureFileNames(file);
			} else {
				ensureFileName(file);
			}
		}
	}

	/**
	 * DOCME add JavaDoc for method ensureFinanzreportFileName
	 * 
	 * @param inputFile
	 * @since 0.1.0
	 */
	private void ensureFinanzreportFileName(File inputFile) {
		logger.debug("ensureFileName " + inputFile);

		StringBuilder sb = new StringBuilder();
		sb.append(NAME_PREFIX).append("_");
		sb.append(getDate(inputFile.getName()).format(OUTPUT_DATE_TIME_FORMATTER));
		sb.append(".pdf");

		File outputFile = new File(inputFile.getParentFile(), sb.toString());
		logger.debug("outputFile: " + outputFile);

		boolean success = inputFile.renameTo(outputFile);
		logger.debug("success: " + success);
	}

	/**
	 * DOCME add JavaDoc for method getDate
	 * 
	 * @param fileName
	 * @return
	 * @since 0.1.0
	 */
	private LocalDate getDate(String fileName) {
		Matcher matcher = FINANZREPORT_INPUT_PATTERN.matcher(fileName);

		if (matcher.matches()) {
			return parseInputDate(matcher.group("date"));
		}

		matcher = FINANZREPORT_OUTPUT_PATTERN.matcher(fileName);

		if (matcher.matches()) {
			return parseOutputDate(matcher.group("date"));
		}

		// TODO implement getDate
		throw new UnsupportedOperationException("'getDate' not implemented for '" + fileName + "'");
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
		LocalDate date = getDate(file.getName());
		logger.debug("date: " + date);

		StringBuilder sb = new StringBuilder();
		sb.append(inboxDirectory);
		sb.append("/");
		sb.append(NAME_PREFIX);
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

		move(file, outputFile);
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
	 * DOCME add JavaDoc for method move
	 * 
	 * @param source
	 * @param target
	 * @throws IOException
	 * @since 0.1.0
	 */
	private void move(File source, File target) throws IOException {
		if (!target.equals(source)) {
			Files.move(source.getAbsoluteFile().toPath(), target.getAbsoluteFile().toPath());
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
