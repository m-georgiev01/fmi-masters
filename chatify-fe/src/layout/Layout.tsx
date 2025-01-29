import React from "react";
import { DefaultProps } from "../models/DefaultProps";
import { Header } from "./Header";
import { Footer } from "./Footer";
import { Main } from "./Main";
import { Box } from "@mui/material";

export class Layout extends React.Component<DefaultProps> {
    render(): React.ReactNode {
        return (
            <Box sx={{textAlign: "center", display: "flex", flexDirection: "column", height: "100vh", backgroundColor: "#F3F4F6"}}>
                <Header state={this.props.state}/>
                <Main state={this.props.state} />
                <Footer />
            </Box>
        )
    }
}