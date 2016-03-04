package model;

public class Attribute
{
   private String id=null;

    private String name=null;
    
    private String value = null;

    private String maxValues=null;

    private String type=null;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

   

    public String getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(String maxValues) {
        this.maxValues = maxValues;
    }

   

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+", max-values = "+maxValues+", type = "+type+"]";
    }
}
			
			