"use client";
import { useEffect, useState } from "react";
import {
  List,
  ListItem,
  IconButton,
  ListItemButton,
  ListItemIcon,
  Checkbox,
  ListItemText,
  Alert,
  CircularProgress,
  Button,
  Typography,
} from "@mui/material";

import axios from "axios";
import { formatDateTypeArray } from "@/util";
import { useRouter } from "next/navigation";
import CustomStatus from "@/generic/CustomStatus";

const ShipForm = () => {
  const [orderList, setOrderList] = useState([]);
  const [checked, setChecked] = useState([]);
  const [uiState, setUiState] = useState();
  const router = useRouter();

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  // Handle form submission
  const handleCreateShip = async (e) => {
    setUiState({ loading: true });
    const userId = localStorage.getItem("id");
    const requestBody = {
      userId,
      orders: checked,
    };

    try {
      await axios.post("http://localhost:8080/api/v1/ship", requestBody);
      router.push("/admin/ship");
      setUiState({ loading: false, success: "Create Ship successfully" });
    } catch (error) {
      const message = error?.response?.data?.message;
      setUiState({
        loading: false,
        error: message,
      });
    }
  };

  const getListOrder = async () => {
    try {
      setUiState({ loading: true });
      const res = await axios.get("http://localhost:8080/api/v1/order");
      setOrderList(res?.data);
      setUiState({ loading: false });
    } catch (error) {
      const message = error?.response?.data?.message;
      setUiState({
        loading: false,
        error: message,
      });
    }
  };

  useEffect(() => {
    getListOrder();
  }, []);

  return (
    <div className="h-full min-h-screen p-4 flex flex-col items-center justify-start space-y-1">
      <Typography
        variant="h3"
        gutterBottom
        sx={{ fontWeight: "bold", textAlign: "center", mb: 2 }}
      >
        Create Shipping for Order
      </Typography>
      {uiState?.success && (
        <Alert color="success" severity="success">
          {uiState?.success}
        </Alert>
      )}
      {uiState?.error && (
        <Alert color="error" severity="error">
          {uiState?.error}
        </Alert>
      )}
      {!uiState?.loading ? (
        <List
          sx={{
            width: "80%",
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "white",
            borderRadius: "12px",
            bgcolor: "background.paper",
          }}
          className="space-y-2"
        >
          <h1 className="font-bold text-xl">Select Orders:</h1>
          <div className="border border-solid rounded-lg max-h-[500px] overflow-y-scroll p-1 w-full">
            {orderList?.map((order, index) => {
              const labelId = `checkbox-list-label-${index}`;

              return (
                <ListItem key={index} disablePadding>
                  <ListItemButton
                    role={undefined}
                    onClick={handleToggle(order?.id)}
                    dense
                  >
                    <ListItemIcon>
                      <Checkbox
                        edge="start"
                        checked={checked?.includes(order?.id)}
                        tabIndex={-1}
                        disableRipple
                        inputProps={{ "aria-labelledby": labelId }}
                      />
                    </ListItemIcon>
                    <h1>{order?.ship}</h1>
                    <ListItemText
                      sx={{
                        display: "flex",
                        justifyItems: "center",
                        justifyContent: "center",
                      }}
                      id={labelId}
                      primary={`${order?.id} - ${formatDateTypeArray(order?.purchaseDate)} - ${order?.user?.username}`}
                    />{" "}
                    <CustomStatus status={order?.status} />
                  </ListItemButton>
                </ListItem>
              );
            })}
          </div>
          <Button fullWidth onClick={() => handleCreateShip()}>
            Create Ship Order
          </Button>
        </List>
      ) : (
        <CircularProgress />
      )}
    </div>
  );
};

export default ShipForm;
