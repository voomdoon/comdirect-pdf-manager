package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager.test;

import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.File;
import java.io.IOException;

/**
 * DOCME add JavaDoc for InboxManagerTest.RunTest
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public abstract class TestBase extends de.voomdoon.testing.tests.TestBase {

	/**
	 * DOCME add JavaDoc for method createNewFile
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @since 0.1.0
	 */
	protected void createNewFileWithDirectory(File file) throws IOException {
		file.getParentFile().mkdirs();
		boolean fileCreated = file.createNewFile();
		assumeThat(fileCreated).isTrue();
	}

	/**
	 * DOCME add JavaDoc for method getInboxDirectory
	 * 
	 * @return
	 * @throws IOException
	 * @since 0.1.0
	 */
	protected String getInboxDirectory() throws IOException {
		String direcoryName = getTempDirectory() + "/inbox/";
		new File(direcoryName).mkdirs();

		return direcoryName;
	}
}