package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.voomdoon.testing.file.TempFileExtension;
import de.voomdoon.testing.file.TempInputDirectory;
import de.voomdoon.testing.file.WithTempInputDirectories;
import de.vooomdoon.finance.budgetbook.comdirect.pdfmanager.test.TestBase;

/**
 * Test class for {@link StatementOfAccountInboxManagerProgram}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class StatementOfAccountInboxManagerProgramTest {

	/**
	 * Test class for {@link StatementOfAccountInboxManagerProgram#main(String[])}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	@ExtendWith(TempFileExtension.class)
	@WithTempInputDirectories(create = true)
	class MainTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void testFinanzreport_atRoot_outputFileExists(@TempInputDirectory String inputDirectory) throws Exception {
			logTestStart();

			File inputFile = new File(inputDirectory + "/Finanzreport_Nr._01_per_01.02.2018110579.pdf");
			File outputFile = new File(inputDirectory + "/Finanzreport/2018/Finanzreport_2018-02-01.pdf");
			createNewFileWithDirectory(inputFile);

			StatementOfAccountInboxManagerProgram.main(new String[] { inputDirectory });

			assertThat(outputFile).exists();
		}
	}
}
