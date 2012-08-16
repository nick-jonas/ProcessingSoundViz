package jonas;

import unlekker.modelbuilder.UGeometry;
import unlekker.modelbuilder.UVec3;
import unlekker.modelbuilder.UVertexList;

public class ObjectField {
	
	private SoundViz 	sv;
	private UGeometry 	mesh;
	
	public ObjectField( SoundViz main )
	{
		sv = main;
	}
	
	public void build()
	{
		setMesh(new UGeometry());
		getMesh().vert.doNoDuplicates = true;
	
		UVertexList horizontal = new UVertexList();
		int numx = 16;//(sv.width - 20) / sv.steps + 1;
		int numy = 16;//(sv.height - 20) / sv.steps + 1;
		
		for(float i = 0; i < numx; i++){
			horizontal.add(i*sv.steps, 0, 0);
		}
		
		UVertexList l[] = UVertexList.getVertexLists(numy);
		for(int i = 0; i < l.length; i++){
			l[i] = new UVertexList(horizontal);
			l[i].translate(0, i* sv.steps, 0);
		}
		
		getMesh().quadStrip(l);
		getMesh().center();
	}
	
	
	public void update(int index, float fftValue)
	{
		getMesh().vert.v[index].z += fftValue * 5;
		
	}

	public UGeometry getMesh() {
		return mesh;
	}

	public void setMesh(UGeometry mesh) {
		this.mesh = mesh;
	}

}
