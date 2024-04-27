package de.vooomdoon.finance.budgetbook.comdirect.pdfmanager;

import java.nio.file.Path;

import de.voomdoon.util.cli.Program;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class StatementOfAccountInboxManagerProgram extends Program {

	/**
	 * @param args
	 * @since 0.1.0
	 */
	public static void main(String[] args) {
		Program.run(args);
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	protected void run() throws Exception {
		new StatementOfAccountInboxManager().run(Path.of(pollArg("inbox-directory")));
	}
}
