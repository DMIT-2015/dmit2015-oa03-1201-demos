package ca.edmonton.data.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

import ca.edmonton.data.entity.ScheduledPhotoEnforcementZoneCentrePoint;

/**
 * An ItemProcessor is executed after an ItemReader has finished.
 *
 * @author Sam Wu
 *
 */
@Named
public class ScheduledPhotoEnforcementZoneCentrePointImportCsvDataBatchItemProcessor implements ItemProcessor {

	/**
	 * Change the return type of this method to the type of object (JsonOject, String, etc) you are processing
	 * Process one item returned from an ItemReader
	 */
	@Override
	public ScheduledPhotoEnforcementZoneCentrePoint processItem(Object item) throws Exception {
		String line = (String) item;
		final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
		String[] values = line.split(delimiter);

		ScheduledPhotoEnforcementZoneCentrePoint model = new ScheduledPhotoEnforcementZoneCentrePoint();
		model.setSiteId(Short.parseShort(values[0]));
		model.setLocationDescription(values[1]);
		model.setSpeedLimit(Short.parseShort(values[2]));
		model.setReasonCodes(values[3].replaceAll("[\"()]", ""));
		model.setLatitude(Double.valueOf(values[4]));
		model.setLongitude(Double.valueOf(values[5]));

		return model;
	}

}