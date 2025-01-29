import { Box, Typography } from "@mui/material";
import React from "react";

export class Footer extends React.Component{
    render(): React.ReactNode {
        return(
            <Box component="footer" sx={{ backgroundColor: "#1E293B", mt: "auto", py: 2, width: "100%"}} >
                <Typography variant="body2" color="#ffffffde">
                    &copy; {new Date().getFullYear()} Chatify. All rights reserved.
                </Typography>
            </Box>
        );
    }
}