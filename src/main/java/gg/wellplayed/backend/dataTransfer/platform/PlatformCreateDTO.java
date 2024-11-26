package gg.wellplayed.backend.dataTransfer.platform;



import gg.wellplayed.backend.model.Platform;

public record PlatformCreateDTO (String name, String img){
	
	public Platform parseToPlatformEntity() {
		Platform p = new Platform();
		p.setName(this.name());
		p.setImg(this.img());
		return p;
	}
}
