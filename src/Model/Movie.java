package Model;

public class Movie
{
    private String id;
    private String overview;
    private String original_title;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOriginalTitle()
    {
        return original_title;
    }

    public void setOriginalTitle(String original_title)
    {
        this.original_title = original_title;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }
}
