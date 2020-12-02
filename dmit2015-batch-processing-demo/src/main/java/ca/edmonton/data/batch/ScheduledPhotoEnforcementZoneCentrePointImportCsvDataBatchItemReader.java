package ca.edmonton.data.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The sequence for a batch chunk step are: ItemReader --> ItemProcessor --> ItemWriter
 *
 * @author Sam Wu
 *
 */
@Named
public class ScheduledPhotoEnforcementZoneCentrePointImportCsvDataBatchItemReader extends AbstractItemReader {

	@Inject
	private JobContext jobContext;

	private BufferedReader reader;

	/**
	 * The open method is used to open a data source for reading.
	 */
	@Override
	public void open(Serializable checkpoint) throws Exception {
		Properties jobParametes = jobContext.getProperties();
		String inputFile = jobParametes.getProperty("input_file");
		reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(inputFile)));
		// Skip the first line as it contains field name headers
		reader.readLine();
	}

	/**
	 * Read from the data source one item at a time.
	 * Return null to trigger the end of the file.
	 */
	@Override
	public Object readItem() throws Exception {
		try {
			String line = reader.readLine();
			return line;
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}