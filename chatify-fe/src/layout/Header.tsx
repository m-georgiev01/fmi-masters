import { AppBar, Box, Toolbar, Typography } from "@mui/material";
import React from "react";
import { DefaultProps } from "../models/DefaultProps";

export class Header extends React.Component<DefaultProps>{
    render(): React.ReactNode {
        return(
            <AppBar position="sticky" sx={{ backgroundColor: "#1E293B", color: "#ffffffde", p: "2px 20px"}}>
                <Toolbar sx={{ justifyContent: "space-between" }}>
                    <Typography variant="h5">
                        Chatify
                    </Typography>
                    <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
                        <Typography>Hi, {this.props.state.user?.username}!</Typography>
                        <Typography sx={{ cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={() => this.props.state.changeIsCreateModalOpen(true)}>Create Channel</Typography>
                        <Typography sx={{ cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={this.props.state.logoutUser}>Logout</Typography>
                    </Box>
                </Toolbar>
            </AppBar>
        );
    }
}