package org.registrator.community.entity;


import javax.persistence.*;

@Entity
@Table(name = "store_of_line_sizes")
public class StoreOfLineSizes {
	
	@Id
	@Column(name = "store_of_line_sizes_id")
	@GeneratedValue
	private Integer StoreOfLineSizesId;

	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource resource;
		
	@Column(name = "minimal_value", nullable = false)
	private Double minValue;

	@Column(name = "maximal_value", nullable = false)
	private Double maxValue;
	
	@ManyToOne
	@JoinColumn(name = "lines_size_id", nullable = false)
	private LineSize lineSize;
	
	public StoreOfLineSizes() {
		
	}

	public Integer getStoreOfLineSizesId() {
		return StoreOfLineSizesId;
	}

	public void setStoreOfLineSizesId(Integer storeOfLineSizesId) {
		StoreOfLineSizesId = storeOfLineSizesId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public LineSize getLineSize() {
		return lineSize;
	}

	public void setLineSize(LineSize lineSize) {
		this.lineSize = lineSize;
	}

}
