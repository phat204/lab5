package phatdtph37313.fpoly.lab5;

public class ShoeDTO {
    private String name;
    private String url;
    private String _id;

    public
    ShoeDTO(String name, String url) {
        this.name = name;
        this.url = url;
        this._id = _id;
    }

    public
    ShoeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "ShoeDTO{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", id='" + _id + '\'' +
                '}';
    }
}
