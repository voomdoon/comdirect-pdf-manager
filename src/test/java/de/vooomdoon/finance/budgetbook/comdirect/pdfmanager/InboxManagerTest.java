package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.vooomdoon.finance.budgetbook.comdirect.pdfmanager.test.TestBase;

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
		class GroupTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_groupByYear_inputFileIsGone() throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport_2018-02-01.pdf");
				createNewFileWithDirectory(inputFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(inputFile).doesNotExist();
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_groupByYear_outputFileExists() throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport_2018-02-01.pdf");
				File outputFile = new File(getInboxDirectory() + "Finanzreport/2018/Finanzreport_2018-02-01.pdf");
				createNewFileWithDirectory(inputFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(outputFile).exists();
			}
		}

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
			 * DOCME add JavaDoc for method test_ignoreOtherFiles
			 * 
			 * @since 0.1.0
			 */
			@Test
			void test_ignoreOtherFiles() throws Exception {
				logTestStart();

				File ignoredFile = new File(getInboxDirectory() + "something_per_01.02.2018110579.pdf");
				createNewFileWithDirectory(ignoredFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(ignoredFile).exists();
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_atRoot_inputFileIsGone() throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport_Nr._01_per_01.02.2018110579.pdf");
				createNewFileWithDirectory(inputFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(inputFile).doesNotExist();
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_atRoot_outputFileExists() throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport_Nr._01_per_01.02.2018110579.pdf");
				File outputFile = new File(getInboxDirectory() + "Finanzreport/2018/Finanzreport_2018-02-01.pdf");
				createNewFileWithDirectory(inputFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(outputFile).exists();
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void testFinanzreport_atTargetDirectory_inputFileIsGone() throws Exception {
				logTestStart();

				File inputFile = new File(
						getInboxDirectory() + "Finanzreport/2018/Finanzreport_Nr._01_per_01.02.2018110579.pdf");
				createNewFileWithDirectory(inputFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(inputFile).doesNotExist();
			}

			/**
			 * @since 0.1.0
			 */
			@ParameterizedTest
			@ValueSource(strings = { "Finanzreport_Nr._01_per_01.02.2018110579.pdf",
					"Finanzreport_Nr._01_per_01.02.2018A1057B.pdf", "Finanzreport_Nr._01_per_01.02.2018_A1057B.pdf",
					"Finanzreport_Nr._01_vom_01.02.2018110579.pdf", "Finanzreport_Nr.01_vom_01.02.2018110579.pdf",
					"Finanzreport_Nr.1_vom_01.02.2018110579.pdf", "Finanzreport_Nr.001_vom_01.02.2018110579.pdf",
					"Finanzreport_Nr.01vom01.02.2018110579.pdf", "FinanzreportNr.01vom01.02.2018110579.pdf" })
			void testFinanzreport_atTargetDirectory_outputFileExists(String fileName) throws Exception {
				logTestStart();

				File inputFile = new File(getInboxDirectory() + "Finanzreport/2018/" + fileName);
				File outputFile = new File(getInboxDirectory() + "Finanzreport/2018/Finanzreport_2018-02-01.pdf");
				createNewFileWithDirectory(inputFile);

				new InboxManager().run(Path.of(getInboxDirectory()));

				assertThat(outputFile).exists();
			}
		}
	}
}
