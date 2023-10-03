import { Alert, Button, Snackbar } from "@mui/material";
import { green } from "@mui/material/colors";
import React from "react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { currentUser, register } from "../../Redux/Auth/Action";
import { useEffect } from "react";

const Signup = () => {
  const [openSnackbar, setOpenSnackbar] = useState(false);
const navigate = useNavigate();
const [inputData, setInputData] = useState({ full_name: "", email: "", password: "" });
const { auth } = useSelector(store => store);
const token = localStorage.getItem("token");
const dispatch = useDispatch();

console.log('current user', auth.reqUser);

const validateForm = () => {
  const { full_name, email, password } = inputData;

  // Define your validation conditions here
  if (full_name.trim() === "") {
    alert("Please enter a full name");
    return false;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    alert("Please enter a valid email address");
    return false;
  }

  if (password.length < 6) {
    alert("Password must be at least 6 characters long");
    return false;
  }

  return true;
};

const handleSubmit = (e) => {
  e.preventDefault();

  if (validateForm()) {
    console.log("handle submit", inputData);
    dispatch(register(inputData));
    setOpenSnackbar(true);
  }
};

  const handleChange = (e) => {
    const {name,value}=e.target;
    setInputData((values)=>({...values,[name]:value}))
  };
  const handleSnackbarClose = () => {

    setOpenSnackbar(false);
  };

  useEffect(()=>{
if(token)dispatch(currentUser(token))
  },[token])

  useEffect(()=>{
if(auth.reqUser?.full_name){
    navigate("/")
}
  },[auth.reqUser])
  return (
    <div>
      <div>
        <div className="flex flex-col justify-center min-h-screen items-center">
          <div className="w-[30%] p-10 shadow-md bg-white">
            <form onSubmit={handleSubmit} className="space-y-5">
              <div>
                <p className="mb-2">User Name</p>
                <input
                  className="py-2 px-3 outline outline-green-600 w-full rounded-md border-1"
                  type="text"
                  placeholder="Enter username"
                  name="full_name"
                  onChange={(e) => handleChange(e)}
                  value={inputData.full_name}
                />
              </div>

              <div>
                <p className="mb-2">Email</p>
                <input
                  className="py-2 px-3 outline outline-green-600 w-full rounded-md border-1"
                  type="text"
                  placeholder="Enter your Email"
                  name="email"
                  onChange={(e) => handleChange(e)}
                  value={inputData.email}
                />
              </div>

              <div>
                <p className="mb-2">Password</p>
                <input
                  className="py-2 px-2 outline outline-green-600 w-full rounded-md border-1"
                  type="password"
                  placeholder="Enter your Password"
                  name="password"
                  onChange={(e) => handleChange(e)}
                  value={inputData.password}
                />
              </div>
              <div>
                        <Button type='submit' sx={{bgcolor:green[700], padding:".5rem 0rem"}} className='w-full bg-green-600' variant='contained'>Sign Up</Button>
                    </div>
             
            </form>
            <div className="flex space-x-3 item-center mt-5">
              <p className="">Already Have Account?</p>
              <Button variant='text' onClick={()=>navigate("/signin")}>sign in</Button>
            </div>
          </div>
        </div>
        <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleSnackbarClose}>
  <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
    Your Account Successfully Created!
  </Alert>
</Snackbar>
      </div>
    </div>
  );
};

export default Signup;
