package com.example.eshik;

public class ImageUpload {





    public String folder;
    public String name;
    public String url;

    public ImageUpload()
    {

    }
    public String getFolder() {
        return folder;
    }


    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

    public ImageUpload(String name,String url,String folder){
        this.name=name;
        this.url=url;
        this.folder=folder;

    }

}

