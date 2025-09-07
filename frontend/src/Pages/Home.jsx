import { useState } from "react";
import { FiUpload } from "react-icons/fi";

export default function Home(){
     


    return(
        <div className="min-h-screen bg-slate-900">
            {/*navbar*/}
            <header className="bg-slate-800 p-4 flex justify-between items-center">
                <h2 className="text-2xl font-bold text-indigo-600">DFS Dashboard</h2>
                <div className="flex items-center gap-4">
                    <span className="text-indigo-600 font-bold">Hello, (Username)</span>
                    <button className="px-3 py-1 rounded bg-indigo-600 text-indigo-300 transition-color duration-500 hover:bg-indigo-200 hover:text-indigo-600">Logout</button>
                </div>
            </header>
            {/*Search and upload*/}
            <section className="flex justify-between p-6 items-center gap-6">
                <div className="bg-slate-800 flex items-center px-3 py-2 rounded w-1/2">
                    <input 
                        type="text"
                        placeholder="Search files..."
                        className="ml-2 outline-none flex-grow text-slate-100 p-2 bg-slate-600 rounded"
                    />
                </div>
                <div className="bg-slate-800 flex items-center px-3 py-2 rounded w-1/2">
                    <button className="flex items-center gap-2 px-4 py-2 bg-indigo-400 text-indigo-200 rounded hover:bg-indigo-200 hover:text-indigo-600 transition-colors duration-500 font-bold">Upload <FiUpload /></button>
                </div>
            </section>
        </div>
    )
}