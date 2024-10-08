import React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import styled from "styled-components";

const NoLineTableCell = styled(TableCell)(({ theme }) => ({
  borderBottom: "none",
}));

const ProductTable = (products) => {
  return (
    <TableContainer>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <NoLineTableCell>
              <p className="font-bold">Product Name</p>
            </NoLineTableCell>
            <NoLineTableCell align="right">
              <p className="font-bold">Category</p>
            </NoLineTableCell>
            <NoLineTableCell align="right">
              <p className="font-bold">Quantity</p>
            </NoLineTableCell>
            <NoLineTableCell align="right">
              <p className="font-bold">Price</p>
            </NoLineTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {products?.products?.map((product, index) => (
            <TableRow
              key={index}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <NoLineTableCell component="th" scope="row">
                {product?.productName}
              </NoLineTableCell>
              <NoLineTableCell align="right">
                {product.categoryName || "Unspecify"}
              </NoLineTableCell>
              <NoLineTableCell align="right">
                {product?.holdAmount}
              </NoLineTableCell>
              <NoLineTableCell align="right">{product?.price}</NoLineTableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};
export default ProductTable;
