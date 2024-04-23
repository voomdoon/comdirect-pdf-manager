package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class InboxManagerTest {

	/**
	 * DOCME add JavaDoc for InboxManagerTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class RunTest extends TestBase {

		/**
		 * DOCME add JavaDoc for InboxManagerTest.RunTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class RenameTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_inputFileIsGone() throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport_Nr._01_per_01.02.2018110579.pdf");
				boolean fileCreated = inputFile.createNewFile();
				assumeThat(fileCreated).isTrue();

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(inputFile).doesNotExist();
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_outputFileExists() throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport_Nr._01_per_01.02.2018110579.pdf");
				File outputFile = new File(getInboxDirectory() + "Finanzreport_2018-02-01.pdf");
				logger.debug("inputFile: " + inputFile);
				boolean fileCreated = inputFile.createNewFile();
				assumeThat(fileCreated).isTrue();

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(outputFile).exists();
			}

			/**
			 * DOCME add JavaDoc for method getInboxDirectory
			 * 
			 * @return
			 * @throws IOException
			 * @since 0.1.0
			 */
			private String getInboxDirectory() throws IOException {
				String direcoryName = getTempDirectory() + "/inbox/";
				new File(direcoryName).mkdirs();

				return direcoryName;
			}
		}
	}
}
