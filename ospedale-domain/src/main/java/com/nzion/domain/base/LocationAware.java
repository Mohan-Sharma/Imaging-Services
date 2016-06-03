package com.nzion.domain.base;

import com.nzion.domain.Location;

public interface LocationAware {
	
	Location getLocation();
	
	void setLocation(Location location);

}
