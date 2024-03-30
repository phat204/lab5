var express = require("express");
var router = express.Router();

const Distributors  = require("./distributors");

router.get("/", (rq, rs) => {
  rs.send("Vao API mobile");
});

// Lấy danh sách các sản phẩm giày
router.get("/get-list-distributors", async (rq, rs) => {
  try {
    const data = await Distributors .find();
    rs.send(data);
  } catch (error) {
    console.log(error);
    rs.status(500).json({
      status: 500,
      messenger: "Internal Server Error",
      data: [],
    });
  }
});

// Thêm mới một sản phẩm giày
router.post("/post-distributors", async (rq, rs) => {
  try {
    const data = rq.body;
    const newShoe = new Distributors ({
      name: data.name,
    });
    const result = await newShoe.save();

    rs.status(201).json({
      status: 201,
      messenger: "Create shoe successfully",
      data: result,
    });
  } catch (error) {
    console.log(error);
    rs.status(500).json({
      status: 500,
      messenger: "Internal Server Error",
      data: [],
    });
  }
});

// Xóa một sản phẩm giày dựa trên ID
router.delete("/delete-distributors-by-id/:id", async (rq, rs) => {
  try {
    const { id } = rq.params;
    const result = await Distributors.findByIdAndDelete(id);

    if (result) {
      rs.json({
        status: 200,
        messenger: "Delete shoe successfully",
        data: result,
      });
    } else {
      rs.status(404).json({
        status: 404,
        messenger: "Shoe not found",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
    rs.status(500).json({
      status: 500,
      messenger: "Internal Server Error",
      data: [],
    });
  }
});

// Cập nhật thông tin một sản phẩm giày dựa trên ID
router.put("/update-distributors-by-id/:id", async (rq, rs) => {
  try {
    const { id } = rq.params;
    const data = rq.body;

    const updateShoe = await Distributors.findByIdAndUpdate(id, data, { new: true });

    if (updateShoe) {
      rs.json({
        status: 200,
        messenger: "Update shoe successfully",
        data: updateShoe,
      });
    } else {
      rs.status(404).json({
        status: 404,
        messenger: "Shoe not found",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
    rs.status(500).json({
      status: 500,
      messenger: "Internal Server Error",
      data: [],
    });
  }
});

router.get('/search-distributors',async(req,res)=>{
  try {
    const key = req.query.key;
    const data = await Distributors.find({name:{"$regex":key,"$options":i}}).sort({createAt:-1});
    if(data) {
      res.json({
        "status":200,
        "messenger":"Thành công",
        "data":data
      })
    }else{
      res.json({
        "status":400,
        "messenger":"Lỗi, không thể thực hiện",
        "data":[]
      })
    }
  } catch (error) {
    console.log(error);
  }
})

module.exports = router;