import FileSaver from "file-saver";
import XLSX from "xlsx/dist/xlsx.full.min"; // 修正导入路径

const htmlToExcel = {
  getExcel(dom, title = "默认标题") {
    try {
      const table = document.querySelector(dom);
      if (!table) throw new Error("表格元素未找到");

      const wb = XLSX.utils.table_to_book(table);
      const wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });

      FileSaver.saveAs(
        new Blob([wbout], { type: "application/octet-stream" }),
        `${title}.xlsx`
      );
    } catch (e) {
      console.error("导出失败:", e);
    }
  }
};

export default htmlToExcel;