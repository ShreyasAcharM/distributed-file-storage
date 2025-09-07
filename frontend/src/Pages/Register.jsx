import axios from "axios";
import { useState } from "react";

export default function Register(){
    const[username, setUsername] = useState("");
    const[password,setPassword] = useState("");
    const[message,setMessage] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();
        try{
            const res = await axios.post("/dfs/register", {
                username,
                password
            });
            setMessage(res.data);
        }catch(err){
            setMessage(err.response?.data || "Registration failed");
        }
    };

    return(
    <div className="flex flex-col items-center justify-center h-screen bg-slate-900 text-slate-100">
      <h2 className="text-3xl font-bold m-4 text-indigo-400 font-serif">Register</h2>
      <form onSubmit={handleRegister} className="flex flex-col items-center gap-4 bg-slate-800 rounded-2xl h-[50%] w-[20%] p-4 justify-center">
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="bg-slate-500 rounded-lg h-10 p-2 text-slate-800"
        /><br />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="bg-slate-500 rounded-lg h-10 p-2 text-slate-800"
        /><br />
        <button type="submit" className="bg-indigo-600 py-4 px-6 rounded-3xl text-indigo-100 hover:bg-indigo-200 hover:text-indigo-600 transition-color duration-500 text-xl">Register</button>
      </form>
      <p className="text-indigo-600 m-3 text-xl font-bold">{message}</p>
    </div>
    )
}