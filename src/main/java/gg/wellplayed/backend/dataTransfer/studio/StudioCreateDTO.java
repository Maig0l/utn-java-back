package gg.wellplayed.backend.dataTransfer.studio;

import gg.wellplayed.backend.model.Studio;
import gg.wellplayed.backend.model.Studio.StudioType;

public record StudioCreateDTO (
		String name,
		StudioType type,
		String site
		) {
	
	public Studio parseToStudioEntity() {
		Studio s = new Studio();
		s.setName(this.name());
		s.setType(this.type());
		s.setSite(this.site);
		return s;
	}
}


