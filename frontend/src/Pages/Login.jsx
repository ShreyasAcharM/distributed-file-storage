import axios from "axios";
import { useState } from "react";

export default function login(){
     const [username, setUsername] = useState("");
     const [password, setPassword] = useState("");
     const [message, setMessage] = useState("")

     const handleLogin = async (e) => {
        e.preventDefault();
        try{
            const res = await axios.post("dfs/login", {
                username,
                password,
            });
            setMessage(res.data);
        }catch(err){
            setMessage(err.response?.data || "Login Failed");
        }
     };

     return(
        <div className="flex flex-col items-center justify-center h-screen bg-violet-200">
            <h2 className="text-3xl font-bold m-4 text-violet-500 font-serif">LOGIN</h2>
                <form onSubmit={handleLogin} className="flex flex-col items-center gap-4 bg-violet-400 rounded-2xl h-[50%] w-[20%] p-4 justify-center">
                <input 
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="bg-white rounded-lg h-10 p-2"
                /><br />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="bg-white rounded-lg h-10 p-2"
                /><br />
                <button type="submit" className="bg-violet-600 p-4 rounded-3xl text-violet-100 hover:bg-violet-200 hover:text-violet-600 transition-color duration-500 text-xl">- Go -</button>
            </form>
            <p className="text-violet-600 m-3 text-xl font-bold">{message}</p>
        </div>
     );
}