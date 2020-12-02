package ca.edmonton.data.batch;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ca.edmonton.data.entity.ScheduledPhotoEnforcementZoneCentrePoint;

/**
 * An ItemWriter is executed after an ItemProcessor has executed.
 *
 * @author Sam Wu
 *
 */
@Named
public class ScheduledPhotoEnforcementZoneCentrePointImportCsvDataBatchItemWriter extends AbstractItemWriter {

	@PersistenceContext(unitName = "h2database-jpa-pu")
	private EntityManager entityManager;

	/**
	 * Write a list of items returned from the ItemProcessor to a destination data source..
	 */
	@Transactional
	@Override
	public void writeItems(List<Object> items) throws Exception {
		items.forEach( item -> {
			ScheduledPhotoEnforcementZoneCentrePoint model = (ScheduledPhotoEnforcementZoneCentrePoint) item;
			entityManager.persist(model);
		});

	}

}