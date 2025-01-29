import { Box, Button, Link, TextField, Typography } from "@mui/material";
import { observer } from "mobx-react";
import React from "react";
import { DefaultProps } from "../models/DefaultProps";
import { navigateTo } from "../navigation";

@observer
export class Login extends React.Component<DefaultProps> {
  render(): React.ReactNode {
    return (
      <Box sx={{textAlign: "center", display: "flex", flexDirection: "column", height: "100vh", backgroundColor: "#F3F4F6", justifyContent: "center", alignItems: "center"}}>
        <Box maxWidth="30vw" padding={2} borderRadius="25px" sx={{ bgcolor: "#1E293B" }} >
            <Typography variant="h4" gutterBottom fontWeight="bold">
              Login
            </Typography>
            <TextField
              required
              label="Username"
              variant="outlined"
              fullWidth
              margin="normal"
              value={this.props.state.username}
              onChange={(e) => this.props.state.saveUsername(e.target.value)}
              sx={{
                '& label.Mui-focused': {
                  color: '#ffffffde',
                },
                '& .MuiOutlinedInput-root': {
                  '&.Mui-focused fieldset': {
                    borderColor: '#ffffffde',
                  },
                  '& fieldset': {
                    borderColor: '#ffffffde',
                  },
                  '&:hover fieldset': {
                    borderColor: '#ffffffde',
                  },
                },
                '& .MuiInputLabel-root': {
                  color: '#ffffffde',
                },
                '& input': {
                  color: '#ffffffde',
                },
              }}
            />
            <TextField
              required
              label="Password"
              type="password"
              variant="outlined"
              fullWidth
              margin="normal"
              value={this.props.state.password}
              onChange={(e) => this.props.state.savePassword(e.target.value)}
              sx={{
                '& label.Mui-focused': {
                  color: '#ffffffde',
                },
                '& .MuiOutlinedInput-root': {
                  '&.Mui-focused fieldset': {
                    borderColor: '#ffffffde',
                  },
                  '& fieldset': {
                    borderColor: '#ffffffde',
                  },
                  '&:hover fieldset': {
                    borderColor: '#ffffffde',
                  },
                },
                '& .MuiInputLabel-root': {
                  color: '#ffffffde',
                },
                '& input': {
                  color: '#ffffffde',
                },
              }}
            />
            <Button
              variant="contained"
              color="primary"
              fullWidth
              onClick={this.props.state.loginUser}
              sx={{ mt: 2 }}
            >
              Login
            </Button>
            <Typography variant="body2" sx={{ mt: 2 }}>
              Don't have an account? 
              <Link
                component="button"
                onClick={() => navigateTo("/register")}
                underline="hover"
                sx={{ ml: 0.5}}
              >
                Register
              </Link>
            </Typography>
          </Box>
        </Box>
    )
  }
}